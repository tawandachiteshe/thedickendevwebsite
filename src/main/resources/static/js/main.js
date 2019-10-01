console.log("hello from spring");
var d = new Date();


$(document).ready(function(){
    $('.sidenav').sidenav();
       $('.tabs').tabs();
       $('.collapsible').collapsible();
       $('.refreshbtn').click(function(){
        console.log('hello jquery')
        updateDocument();
    });
  });

  var a = 0;
    updateDocument();
    

  function updateDocument (){
     
        
        
      
  }

 function getdata (){
    var request = new XMLHttpRequest();
  request.open("get","api/v1/serverinfo/time");
  request.onreadystatechange = function(){
  var div = document.getElementsByClassName("divHeader");
   if(request.readyState === this.DONE && this.status === 200){

            if(this.responseText !== null){
             div[0].innerText = this.responseText;
            console.log(request.responseText)

            }else{

             div[0].innerText = this.status

            }

       }

       }

       request.send();

  }

  