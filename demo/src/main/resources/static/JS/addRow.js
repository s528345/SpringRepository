async function addRow(){
    //Getting values
    const name = document.getElementById('name').value;
    const age = document.getElementById('age').value;
    const classes = document.getElementById('classes').value;

// validation of inputs here

    if((name != null && name.length !== 0 && "string" === typeof(name)) &&
        (classes != null && classes.length !== 0 && "string" === typeof(classes))) {
        await fetchAPI("/rest/api/frontEnd")
        const table = document.getElementsByTagName('table')[0];
        const newRow = table.insertRow(1);


        //Adding new row
        const cell1 = newRow.insertCell(0);
        const cell2 = newRow.insertCell(1);
        const cell3 = newRow.insertCell(2);

        //Adding new data into new row
        cell1.innerHTML = name;
        cell2.innerHTML = age;
        cell3.innerHTML = classes;
    }
    else{
        console.log("Invalid Input detected")
        throw new Error
    }
    /* here's what should happen. I kind-of see what you're doing.
    1) extract and validate inputs (not empty, no injections, correct data type varying on your input tag's attributes)
    2) call the fetchAPI function -- see comment below. It should be in a function.
    */


}

// todo: add rest api call and handler -- using ajax in here
// /rest/api/frontEnd
// localhost:8080/...

// this should be an asynchronous function OR caller (addRow) is asynchronous-- called by the button click handler
async function fetchAPI(url){

    fetch(url, {

        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify({
            name: "${name}",
            age: "${age}",
        })
    }).then(
        (data) => {
            jsonHandler(data);
        },
        (errorReason) => {
            console.log(errorReason);
            // if api function failed -- not soft fail like bad data (ie: bad login) but something bad happened
        })
}
// can be synchronous -- why is that?
function jsonHandler(data){
    try{
        // unwrap data given it's a json document
        if(data.ok){
            // Dynamically create classes from json document
            let json = data.json()
            console.log(json)
        }
        else{
            console.log("HTTP-ERROR: ", data.status)
            document.getElementById('status').innerHTML = data.status
        }
        // check status if success, if so then unwrap data and create new table cells for each class
        // else (failed), see why and communicate to view/user (set a hidden text field and make it visible)
    }
    catch(error){
        // nothing in the response handler falls out of method scopes
        console.log("HTTP-ERROR: ", error, data.status)
    }
}
