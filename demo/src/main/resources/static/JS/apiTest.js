
// todo: add rest api call and handler -- using ajax in here
// /rest/api/frontEnd
// localhost:8080/...

// this should be an asynchronous function OR caller (addRow) is asynchronous-- called by the button click handler
async function fetchAPI(url) {
    fetch(url)
        .then(response => {
            if(response.ok){
                return response.json();
            }
            else{
                console.log("An Error Occurred")
                throw Error("Error");
            }
        })
        .then(data => {
            console.log(data);

        },
            error => {
                console.log(`error in fetch api: ${error}`);
            })
        .catch(error =>{
            console.log(error);
        })

}

//fetching api handler call to add rows to view
async function addRows(){
    await fetchAPI("\"/rest/api/frontEnd\"")
}
function postData(name, age, classes){
    fetch("/rest/api/frontEnd", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify({  // already now data types -- so why put into strings?
            name: name,
            age: age,
        })
    })
        .then(response => {
            if(response.ok){
                return response.json();
            }
            else{
                console.log("An Error Occurred")
                throw Error("Error");
            }
        },
            errorReason => {
            console.log(`an error occured in the fetch api\nreason: ${errorReason}`);
            })
        .then(data => {
            console.log(data)
        });
}
//Set with Add Student button to add a single row to the table
async function addRow(){
    //Getting values
    const name = document.getElementById('name').value;
    const age = parseInt(document.getElementById('age').value, 10 );
    const classes = document.getElementById('classes').value;

// validation of inputs here
    if((name != null && name.length !== 0 && "string" === typeof(name)) &&
        (classes != null && classes.length !== 0 && "string" === typeof(classes))) {
        //Create post
        postData(name, age, classes);
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