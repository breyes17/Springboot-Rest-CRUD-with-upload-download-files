$(function(){
	
	
	let initLoad = () => {
		$.ajax({
			url : "/api/info",
			type : "GET",
			contentType : "application/json",
			success : e => {
				e.forEach( i => newData(i));
			}, 
			error : e =>{
				console.log("There is something wrong")
			}
		})
	}
	
	initLoad();
	
	let globalId;
	
	$("#submitBtn").click(e => {
		e.preventDefault();
		
		$(e.target).prop("disabled",true);
		
		let formData = new FormData();
		let fname = $("#inputFirstName");
		let lname = $("#inputLastName");
		let file = $("#customFile");
		let type;
		
		let data = {
				firstName : fname.val(),
				lastName : lname.val()
		}

		for(let k of formData.entries()){
			console.log(k[0]+" : "+k[1])
		}
		
		if($(e.target).text() === "Save"){
//			edit
			data.id = globalId;
			type = "PUT";

		} else {
//			add 
			type = "POST";
		}
		
		formData.append("formData", JSON.stringify(data));
		formData.append("attachedFile", file[0].files[0]);
		
	    $.ajax({
	        type: type,
	        enctype: 'multipart/form-data',
	        url: "/api/info",
	        data: formData,
	        //http://api.jquery.com/jQuery.ajax/
	        //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
	        processData: false, //prevent jQuery from automatically transforming the data into a query string
	        contentType: false,
	        cache: false,
	        timeout: 600000,
	        success: response => {
	        	
	        	if(type === "POST"){
	        		newData(response);
	        	} else {
	        		let parent = $(`a.update[data-id='${response.id}']`).closest("tr").children();
	        		$(parent[0]).text(response.firstName);
	        		$(parent[1]).text(response.lastName);
	        		$(parent[2]).find("a").prop("href",`api/info/download/${response.id}/${response.fileName}`);
	        		$("#submitBtn").text("Add Info");
	        	}

		        $(e.target).prop("disabled",false);
	        	fname.val("");
	        	lname.val("");
	        	file.val("");
	        },
	        error: response => {
	            console.log("ERROR : ", e);
	            alert("There is something wrong");
	            $(e.target).prop("disabled",false);

	        }
	    });
	})
	
//	delete
	$("body").on("click", ".delete", e => {
		let id = $(e.target).data("id");
		console.log($(e.target).parents("tr"))
		$.ajax({
			url : "/api/info/delete/"+id,
			type : "delete",
			success : response => {
				$(e.target).parents("tr").fadeOut(1000);
			},
			error : e => {
				
			}
		})
	})
	
//	update
	$("body").on("click", ".update", e => {
		let parent = $(e.target).parents("tr").children();
		let fname = parent[0];
		let lname = parent[1];
		globalId = $(e.target).data("id");
		console.log(globalId)
		$("#inputFirstName").val($(fname).text())
		$("#inputLastName").val($(lname).text())
		$("#submitBtn").text("Save")
		
	})
	
	const newData = (obj,isAppend = true) => {
    	let html = `
    		<tr>
    			<td>${obj.firstName}</td>
    			<td>${obj.lastName}</td>
    			<td><a href="/api/info/download/${obj.id}/${obj.fileName}" target="_blank" class="btn btn-primary btn-sm">Download File</a></td>
    			<td>
    				<a href="#" data-id="${obj.id}" class="btn btn-secondary btn-sm update">Update</a>
    				<a href="#" data-id="${obj.id}" class="btn btn-danger btn-sm delete">Delete</a>
    			<td/>
    		</tr>
    	`;
    	
    	$("table tbody").append(html);
    	
    	
	}
})