import {useEffect, useState} from 'react';
import React from 'react';
import {getTests, addTest, updateTest, deleteTest} from "./utils/rest-utils";
import TestForm from "./TestForm";
import TestTable from "./TestTable";

export default function TestsApp(){
    const [tests, setTests] = useState([{}]);

    function addFunc(test){
        addTest(test)
        .then(() => getTests())
        .then(tests => setTests(tests))
        .catch(error => console.log('error', error));
    }

    function updateFunc(test){
        updateTest(test)
        .then(() => getTests())
        .then(tests => setTests(tests))
        .catch(error => console.log('error', error));
    }

    function deleteFunc(id){
        deleteTest(id)
        .then(() => getTests())
        .then(tests => setTests(tests))
        .catch(error => console.log('error', error));
    }

    useEffect(() => {
        getTests()
        .then(tests => setTests(tests))
        .catch(error => console.log('error', error));
    })

    return (<div className={TestsApp}>
        <h1>Tests App</h1>
        <div><p>Add a test: </p></div>
        <TestForm addFunc={addFunc} />
        <TestTable tests={tests} updateFunc={updateFunc} deleteFunc={deleteFunc} />
    </div>)
}