import React, {useState} from 'react';

function TestRow({test, updateFunc, deleteFunc}){
    const [updatedPoints, setUpdatedPoints] = useState(test.points);
    const [updatedDate, setUpdatedDate] = useState(test.date);
    const [isEditing, setIsEditing] = useState(false);

    function handleUpdate(){
        test.points = updatedPoints;
        test.date = updatedDate;
        console.log('update test: ', test);
        updateFunc(test);
    }

    function handleDelete(){
        console.log('delete test: ', test);
        deleteFunc(test.id);
    }

    const handleEdit = () => {
        setIsEditing(true);
    }

    const handleCancel = () => {
        setIsEditing(false);
        setUpdatedPoints(test.points);
        setUpdatedDate(test.date);
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        handleUpdate();
        setIsEditing(false);
    }

    return (
        <tr>
            <td>
                {isEditing ? (
                    <input
                        type="text"
                        value={updatedPoints}
                        onChange={e => setUpdatedPoints(e.target.value)}
                    />
                ) : (
                    test.points
                )
                }
            </td>
            <td>
                {isEditing ? (
                    <input
                        type="text"
                        value={updatedDate}
                        onChange={e => setUpdatedDate(e.target.value)}
                    />
                ) : (
                    test.date
                )}
            </td>
            <td>
                {isEditing ? (
                    <>
                        <button onClick={handleSubmit}>Save</button>
                        <button onClick={handleCancel}>Cancel</button>
                    </>
                ) : (
                    <>
                        <button onClick={handleDelete}>Delete</button>
                        <button onClick={handleEdit}>Update</button>
                    </>
                )}
            </td>
        </tr>
    )
}

export default function TestTable({tests, updateFunc, deleteFunc}){
    let rows = []
    tests.forEach(function(test){
        rows.push(<TestRow test={test} key={test.id} updateFunc={updateFunc} deleteFunc={deleteFunc} />
        )
    })
    return (
        <div className={TestTable}>
            <table className="center">
                <thead>
                    <tr>
                        <th>Points</th>
                        <th>Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>{rows}</tbody>
            </table>
        </div>
    )
}