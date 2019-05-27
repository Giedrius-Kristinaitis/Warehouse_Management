var owners = [];
var areas = [];

/**
 * Makes an AJAX request to the server
 * @param {string} method request method to use
 * @param {string} url url of the requested file
 * @param {function} callback callback function to be executed when the request finishes
 * @param {array} headers array of header names
 * @param {array} headerValues array of header values
 * @param {string} parameters request parameters if the method used is POST
 */
function ajax(method, url, callback, headers, headerValues, parameters){
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            if(callback !== undefined){
                callback.apply(this);
            }
        }
    }

    xhttp.open(method, url, true);

    if(headers !== undefined && headerValues !== undefined && headers.length === headerValues.length){
        for(var i = 0; i < headers.length; i++){
            xhttp.setRequestHeader(headers[i], headerValues[i]);
        }
    }

    xhttp.send(parameters);
}

function getOwnerOptions() {
    ajax('GET', 'http://localhost:8080/warehouse/api/item/get_owners', function(){
        owners = this.responseText.split('|');
    });
}

function getStorageAreaOptions() {
    ajax('GET', 'http://localhost:8080/warehouse/api/item/get_areas', function(){
        areas = this.responseText.split('|');
    });
}

var _my_item_id = 0;

function addOptions(options) {
    var optionText = '';

    for (var i = 0; i < options.length - 1; i++) {
        optionText += '<option>' + options[i] + '</option>';
    }

    return optionText;
}

function addHtml() {
    // find the dynamic content div
    var content = document.querySelector("#dynamic-content");

    // make it visible if it is not
    if (content.style.display === 'none') {
        content.style.display = 'initial';
    }

    // add a new div that will hold single stored item
    var item = document.createElement('div');

    item.classList.add('dynamic_item');

    item.innerHTML = '<h5>New Stored Item</h5>' +
        '<label for="count' + (_my_item_id + 1) + '">Count</label>' +
        '<input type="text" class="form-control" name="count' + (_my_item_id + 1) + '" id="count' + (_my_item_id + 1) + '" placeholder="Count"/>' +
        '<div class="form-group">' +
        '<label for="owner' + (_my_item_id + 1) + '">Owner</label>' +
        '<select class="form-control" name="owner' + (_my_item_id + 1) + '" id="owner' + (_my_item_id + 1) + '">' +
        addOptions(owners) +
        '</select>' +
        '</div>' +
        '<div class="form-group">' +
        '<label for="storageAddress' + (_my_item_id + 1) + '">Storage Address</label>' +
        '<select class="form-control" name="storageAddress' + (_my_item_id + 1) + '" id="storageAddress' + (_my_item_id + 1) + '">' +
        addOptions(areas) +
        '</select>' +
        '</div>';

    // create and add a remove item button
    var removeButton = document.createElement('div');
    removeButton.setAttribute('role', 'button');
    removeButton.classList.add('btn');
    removeButton.classList.add('btn-dark');
    removeButton.innerHTML = 'Remove This Item';

    // add a click listener to the remove button
    removeButton.addEventListener('click', function() {
        content.removeChild(item);
    });

    item.appendChild(removeButton);

    // increment item id
    _my_item_id++;

    // append the item
    content.appendChild(item);
}

// execute code only when the content is done loading
window.addEventListener('load', function() {
    getOwnerOptions();
    getStorageAreaOptions();

    var button = document.querySelector("#addStoredItem");

    button.addEventListener('click', function() {
        addHtml();
    });
});