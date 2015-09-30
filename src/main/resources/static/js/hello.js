function unflatten(arr) {
    var tree = [],
        mappedArr = {},
        arrElem,
        mappedElem;

    // First map the nodes of the array to an object -> create a hash table.
    for (var i = 0, len = arr.length; i < len; i++) {
        arrElem = arr[i];
        mappedArr[arrElem.Id] = arrElem;
        mappedArr[arrElem.Id]['children'] = [];
    }


    for (var Id in mappedArr) {
        if (mappedArr.hasOwnProperty(Id)) {
            mappedElem = mappedArr[Id];
            // If the element is not at the root level, add it to its parent array of children.
            if ((mappedElem.ParentId)&&(mappedArr[mappedElem['ParentId']])) {
                    mappedArr[mappedElem['ParentId']]['children'].push(mappedElem);
            }
            // If the element is at the root level, add it to first level elements array.
            else {
                tree.push(mappedElem);
            }
        }
    }
    return tree;
}

function Hello($scope, $http) {
    $http.get('/Proceses/').
        success(function (data) {
            $scope.greeting = unflatten(data);
        });
}