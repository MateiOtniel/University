import React from 'react';
import {useState} from 'react';

export default function TestForm({addFunc}){
    const [points, setPoints] = useState('');
    const [date, setDate] = useState('');

    function handleSubmit(e){
        e.preventDefault();
        addFunc({points, date});
        setPoints('');
        setDate('');
    }

    return (
        <form onSubmit={handleSubmit}>
            <label>
                Points:
                <input type="text" value={points} onChange={e => setPoints(e.target.value)} />
            </label><br />
            <label>
                Date:
                <input type="text" value={date} onChange={e => setDate(e.target.value)} />
            </label><br />

            <input type="submit" value="Add test" />
        </form>);
}