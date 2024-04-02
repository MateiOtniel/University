import {TESTS_BASE_URL} from "./constants";

function status(response){
    console.log('response status' + response.status);
    if(response.status >= 200 && response.status < 300){
        return Promise.resolve(response);
    } else{
        return Promise.reject(new Error(response.statusText));
    }
}

function json(response){
    return response.json();
}

export function getTests(){
    let headers = new Headers();
    headers.append('Accept', 'application/json');
    let myInit = {
        method: 'GET',
        headers: headers,
        mode: 'cors'
    }
    let request = new Request(TESTS_BASE_URL, myInit);

    return fetch(request)
    .then(status)
    .then(json)
    .then(data =>{
        console.log('Request succeeded with JSON response', data);
        return data;
    }).catch(error =>{
        console.log('Request failed', error);
        return Promise.reject(error);
    })
}

export function addTest(test){
let myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type", "application/json");

    let antet = { method: 'POST',
        headers: myHeaders,
        mode: 'cors',
        body: JSON.stringify(test)
    };

    return fetch(TESTS_BASE_URL, antet)
    .then(status)
    .then(response => {
        console.log('response', response);
        return response;
    }).catch(error => {
        console.log('error', error);
        return Promise.reject(error);
    })
}

export function updateTest(test){
    let myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type", "application/json");

    let antet = {
        method: 'PUT',
        headers: myHeaders,
        mode: 'cors',
        body: JSON.stringify(test)
    };

    let updateUrl = TESTS_BASE_URL + '/' + test.id;

    return fetch(updateUrl, antet)
    .then(status)
    .then(response => {
        console.log('response', response);
        return response;
    }).catch(error => {
        console.log('error', error);
        return Promise.reject(error);
    })
}

export function deleteTest(id){
    console.log('Inainte de fetch delete')
    let myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");

    let antet = { method: 'DELETE',
        headers: myHeaders,
        mode: 'cors'
    };

    const testUrl = TESTS_BASE_URL + '/' + id;
    console.log('testUrl: ', testUrl);
    return fetch(testUrl, antet)
    .then(status)
    .then(response => {
        console.log('response', response);
        return response;
    }).catch(error => {
        console.log('error', error);
        return Promise.reject(error);
    })
}