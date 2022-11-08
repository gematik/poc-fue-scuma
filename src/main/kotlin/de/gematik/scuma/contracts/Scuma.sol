// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract ScumaContract {

    address Owner;

    address[] protectionAuthorizationIds;
    mapping(address => uint) protectionAuthorizationIdIndices;
    Policy[] policies;
    Rule[][] ruleLists;
    mapping(uint256 => uint) policyIndices; // protectedResourceId to policy index

    struct Policy {
        uint256 what;  // protected resource id
        Rule[] ruleList;
    }

    struct Rule {
        address who;  // userId
        uint256 how;  // bit set representing the defined access methods; methods defined by application
    }

    struct Permission {
        uint256 protectedResourceId;
        uint256 grantedMethods; // bit set representing the granted access methods; methods defined by application
    }

    struct PermissionRequest {
        uint256 protectedResourceId;
        uint256 requestedMethods; // bit set representing the requested access methods; methods defined by application
    }

    constructor () {
        Owner = msg.sender;
        protectionAuthorizationIds.push();
        // index 0 reserved to indicate no id
        policies.push();
        // index 0 reserved to indicate no id
    }

    modifier onlyOwner(){
        require(msg.sender == Owner, 'Not owner');
        _;
    }

    modifier onlyAuthorizedProviders(){
        require(protectionAuthorizationIdIndices[msg.sender] > 0, 'Provider not authorized');
        _;
    }

    function registerProvider(address protectionAuthorizationId) public onlyOwner {
        protectionAuthorizationIds.push(protectionAuthorizationId);
        protectionAuthorizationIdIndices[protectionAuthorizationId] = protectionAuthorizationIds.length - 1;
    }

    function unregisterProvider(address protectionAuthorizationId) public onlyOwner {
        uint index = protectionAuthorizationIdIndices[protectionAuthorizationId];
        if (index > 0) {
            protectionAuthorizationIds[index] = protectionAuthorizationIds[protectionAuthorizationIds.length - 1];
            protectionAuthorizationIds.pop();
        }
    }

    function getProviderCount() public onlyOwner view returns (uint256){
        return protectionAuthorizationIds.length - 1;
    }

    function getProviders() public onlyOwner view returns (address[] memory){
        address[] memory providerIds = new address[](protectionAuthorizationIds.length - 1);
        for (uint i = 1; i < protectionAuthorizationIds.length; i++) {
            providerIds[i - 1] = protectionAuthorizationIds[i];
        }
        return providerIds;
    }

    function unregisterAllProviders() public onlyOwner {
        for (uint i = 1; i < protectionAuthorizationIds.length; i++) {
            delete protectionAuthorizationIdIndices[protectionAuthorizationIds[i]];
        }
        delete protectionAuthorizationIds;
        protectionAuthorizationIds.push();
    }

    function registerResource(uint256 protectedResourceId) public onlyAuthorizedProviders {
        uint policyIndex = policyIndices[protectedResourceId];
        require(policyIndex == 0, 'rejected - protected resource already exist');
        ruleLists.push();
        policies.push();
        policies[policies.length - 1].what = protectedResourceId;
        policies[policies.length - 1].ruleList = ruleLists[ruleLists.length - 1];
        policyIndices[protectedResourceId] = policies.length - 1;
    }

    function unregisterResource(uint256 protectedResourceId) public onlyAuthorizedProviders {
        uint policyIndex = policyIndices[protectedResourceId];
        require(policyIndex > 0, 'rejected - protected resource does not exist');
        policies[policyIndex] = policies[policies.length - 1];
        policies.pop();
        delete policyIndices[protectedResourceId];
    }

    function getResourceCount() public onlyAuthorizedProviders view returns (uint256){
        return policies.length - 1;
    }

    function getResourceIds() public onlyAuthorizedProviders view returns (uint256[] memory){
        uint256[] memory resourceIds = new uint256[](policies.length - 1);
        for (uint i = 1; i < policies.length; i++) {
            resourceIds[i - 1] = policies[i].what;
        }
        return resourceIds;
    }

    function unregisterAllResources() public onlyAuthorizedProviders {
        for (uint i = 0; i < policies.length; i++) {
            delete policyIndices[policies[i].what];
        }
        delete policies;
        policies.push();
        delete ruleLists;
    }

    function setRule(uint256 protectedResourceId, address userId, uint256 methods) public onlyOwner {
        uint policyIndex = policyIndices[protectedResourceId];
        require(policyIndex > 0, 'protected resource does not exist');
        policies[policyIndex].ruleList.push(Rule(userId, methods));
    }

    function getPolicy(uint256 protectedResourceId) public onlyOwner view returns (Rule[] memory){
        uint policyIndex = policyIndices[protectedResourceId];
        require(policyIndex > 0, 'protected resource does not exist');
        return policies[policyIndex].ruleList;
    }

    function deleteRule(uint256 protectedResourceId, uint index) public onlyOwner {
        uint policyIndex = policyIndices[protectedResourceId];
        require(policyIndex > 0, 'protected resource does not exist');
        Policy storage policy = policies[policyIndex];
        require(index < policy.ruleList.length, 'rule does not exist');
        policy.ruleList[index] = policy.ruleList[policy.ruleList.length - 1];
        policy.ruleList.pop();
    }

    function requestPermissions(address userId, PermissionRequest[] calldata permissionRequests) public onlyAuthorizedProviders view returns (Permission[] memory){
        // first count permissions
        uint count = 0;
        for (uint i = 0; i < permissionRequests.length; i++) {
            PermissionRequest memory permissionRequest = permissionRequests[i];
            uint policyIndex = policyIndices[permissionRequest.protectedResourceId];
            if (policyIndex > 0) {
                Policy storage policy = policies[policyIndex];
                for (uint j = 0; j < policy.ruleList.length; j++) {
                    Rule memory rule = policy.ruleList[j];
                    if (rule.who == userId && (rule.how & permissionRequest.requestedMethods != 0)) {
                        count++;
                    }
                }
            }
        }
        // allocate array and fill
        Permission[] memory permissions = new Permission[](count);
        count = 0;
        for (uint i = 0; i < permissionRequests.length; i++) {
            PermissionRequest memory permissionRequest = permissionRequests[i];
            uint policyIndex = policyIndices[permissionRequest.protectedResourceId];
            if (policyIndex > 0) {
                Policy storage policy = policies[policyIndex];
                for (uint j = 0; j < policy.ruleList.length; j++) {
                    Rule memory rule = policy.ruleList[j];
                    if (rule.who == userId && (rule.how & permissionRequest.requestedMethods != 0)) {
                        permissions[count++] = Permission(permissionRequest.protectedResourceId, rule.how & permissionRequest.requestedMethods);
                    }
                }
            }
        }
        return permissions;
    }
}