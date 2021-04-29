
let uriRoles = uri + "Roles/";
let todos = [];

//Get all Roles
function getAllRoles() {   
    fetch(uriRoles + "GetALLRoles")
        .then(respone => respone.json())
        .then(data => displayItems(data))
        .catch(error => console.error('Unable to get Roles.', error));
}

//Get Role by Id
function getRolebyId() {
    const itemId = document.getElementById('get-id').value;
    let url = `${uriRoles + "GetRolebyId?id="}${itemId}`;

    fetch(url)
        .then(response => response.json())
        .then(data => displayRole(data))
        .catch(error => console.error('Unable to get Role.' , error))

}

//Adds a new Role
function PostNewRole() {
    const addRoleTypeTextbox = document.getElementById('add-name');

    const item = {
        roleType: addRoleTypeTextbox.value.trim()
    };

    fetch(uriRoles + "PostNewRole", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(item)
    })
        .then(response => response.json())
        .then(() => {
            getAllRoles();
            addRoleTypeTextbox.value = '';
        })
        .catch(error => console.error('Unable to add item.', error));

}

//Deletes Role
function deleteRole(id) {
    let url = uriRoles + "DeleteRole?id=" + id;
    fetch( url, {
        method: 'DELETE'
    })
        .then(() => getAllRoles())
        .catch(error => console.error('Unable to delete item.', error));
}


//Displays the Edit Form
function displayEditForm(id) {
    const item = todos.find(item => item.role_Id === id);

    document.getElementById('edit-name').value = item.roleType;
    document.getElementById('edit-id').value = item.role_Id;
    document.getElementById('editForm').style.display = 'block';
}



//Updates Role
function updateItem() {
    const itemId = document.getElementById('edit-id').value;
    const item = {
        role_Id: parseInt(itemId, 10),
        roleType: document.getElementById('edit-name').value.trim()
    };
    let url = `${uriRoles + "UpdateRolebyId?id="}${itemId}`;
    fetch( url, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(item)
    })
        .then(() => getAllRoles())
        .catch(error => console.error('Unable to update item.', error));

    closeInput();

    return false;
}

//Closes the Edit Input
function closeInput() {
    document.getElementById('editForm').style.display = 'none';
}

//Counts the number of rows found
function displayCount(itemCount) {
    const name = (itemCount == 1) ? 'Role' : 'Roles';
    document.getElementById('counter').innerText = `${itemCount} ${name} Found`
}

//Displays the Role found onto the html
function displayRole(data) {
    let table = document.getElementById('gotRole').style.display = 'table';
    const tbody = document.getElementById('todoRole');
    tbody.innerHTML = '';

    let tr = tbody.insertRow();

    let td1 = tr.insertCell(0);
    let textInt = document.createTextNode(data.role_Id);
    td1.appendChild(textInt);

    let td2 = tr.insertCell(1);
    let textType = document.createTextNode(data.roleType);
    td2.appendChild(textType);

}

//Displays all Roles onto html
function displayItems(data) {
    const tbody = document.getElementById('todos');
    tbody.innerHTML = '';

    displayCount(data.length);

    const button = document.createElement('button');

    data.forEach(item => {
       
        let editButton = button.cloneNode(false);
        editButton.innerText = 'Edit';
        editButton.setAttribute('onclick', `displayEditForm(${item.role_Id})`);

        let deleteButton = button.cloneNode(false);
        deleteButton.innerText = 'Delete';
        deleteButton.setAttribute('onclick', `deleteRole(${item.role_Id})`);

        let tr = tbody.insertRow();

        let td22 = tr.insertCell(0);
        let textNode2 = document.createTextNode(item.role_Id);
        td22.appendChild(textNode2);

        let td2 = tr.insertCell(1);
        let textNode = document.createTextNode(item.roleType);
        td2.appendChild(textNode);

        let td3 = tr.insertCell(2);
        td3.appendChild(editButton);

        let td4 = tr.insertCell(3);
        td4.appendChild(deleteButton);
    });

    todos = data;

}