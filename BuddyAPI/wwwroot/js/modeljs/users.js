
let uriUsers = uri + "Users/";
let users = [];

//Get all Roles
function getAllItems() {
    fetch(uriUsers + "GetALLUsers")
        .then(respone => respone.json())
        .then(data => displayItems(data))
        .catch(error => console.error('Unable to get Roles.', error));
}

//Get Item by Id
function getItembyId() {
    const itemId = document.getElementById('get-userid').value;
    let url = `${uriUsers + "GetUserbyId?id="}${itemId}`;


    fetch(url)
        .then(response => response.json())
        .then(data => {

            let roletype;
            getRoleType(data.role_Id).then(role => { roletype = role; });
            displayItem(data, roletype);
        })
        .catch(error => console.error('Unable to get Role.', error))

}

//Adds a new Item
function PostNewItem() {
    const addRoleTypeTextbox = document.getElementById('add-name');

    const item = {
        roleType: addRoleTypeTextbox.value.trim()
    };

    fetch(uriUsers + "PostNewUser", {
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
function deleteItem(id) {
    let url = uriUsers + "DeleteUser?id=" + id;
    fetch(url, {
        method: 'DELETE'
    })
        .then(() => getAllItems())
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
    let url = `${uriUsers + "UpdateUserbyId?id="}${itemId}`;
    fetch(url, {
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
    const name = (itemCount == 1) ? 'User' : 'Users';
    document.getElementById('counter').innerText = `${itemCount} ${name} Found`
}

//Displays the Role found onto the html
function displayItem(item, roletype) {
    let table = document.getElementById('gotRole').style.display = 'table';
    const tbody = document.getElementById('todoRole');
    tbody.innerHTML = '';

    let tr = tbody.insertRow();

    let user_Id = tr.insertCell(0);
    let textId = document.createTextNode(item.user_Id);
    user_Id.appendChild(textId);

    let username = tr.insertCell(1);
    let textUsername = document.createTextNode(item.username);
    username.appendChild(textUsername);

    let telephone = tr.insertCell(2);
    let textTelephone = document.createTextNode(item.telephone);
    telephone.appendChild(textTelephone);

    let email = tr.insertCell(3);
    let textEmail = document.createTextNode(item.email);
    email.appendChild(textEmail);

    let password = tr.insertCell(4);
    let textPassword = document.createTextNode(item.password);
    password.appendChild(textPassword);

    let role_Id = tr.insertCell(5);
    let roleName = getRoleType(roletype);
    let textRoleId = document.createTextNode(roleName);
    role_Id.appendChild(textRoleId);

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
        editButton.setAttribute('onclick', `displayEditForm(${item.user_Id})`);

        let deleteButton = button.cloneNode(false);
        deleteButton.innerText = 'Delete';
        deleteButton.setAttribute('onclick', `deleteItem(${item.user_Id})`);

        let tr = tbody.insertRow();

        let user_Id = tr.insertCell(0);
        let textId = document.createTextNode(item.user_Id);
        user_Id.appendChild(textId);

        let username = tr.insertCell(1);
        let textUsername = document.createTextNode(item.username);
        username.appendChild(textUsername);

        let telephone = tr.insertCell(2);
        let textTelephone = document.createTextNode(item.telephone);
        telephone.appendChild(textTelephone);

        let email = tr.insertCell(3);
        let textEmail = document.createTextNode(item.email);
        email.appendChild(textEmail);

        let password = tr.insertCell(4);
        let textPassword = document.createTextNode(item.password);
        password.appendChild(textPassword);

        let role_Id = tr.insertCell(5);
        let textRoleId = document.createTextNode(item.role_Id);
        role_Id.appendChild(textRoleId);

        let td3 = tr.insertCell(6);
        td3.appendChild(editButton);

        let td4 = tr.insertCell(7);
        td4.appendChild(deleteButton);
    });

    users = data;

}