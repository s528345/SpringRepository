async function addRow(){
    //Getting values
    const name = document.getElementById('name').value;
    const age = document.getElementById('age').value;
    const classes = document.getElementById('classes').value;

// validation of inputs here
    await fetchAPI("/rest/api/frontEnd")
    if((name != null && name.length !== 0 && "string" === typeof(name)) &&
        (classes != null && classes.length !== 0 && "string" === typeof(classes))) {
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
await fetchAPI("/rest/api/frontEnd")
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
            console.log(data.data);
            // const html = data.data
            //     .map( => {
            //         return `
            //         <tr>
            //
            //         </tr>
            //         `
            //
            //
            //     })
        })

}
