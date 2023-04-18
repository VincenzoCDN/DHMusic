let nino = document.getElementById('nino')
let nino2 = document.getElementById('nino2')


nino.onclick= function(){
    let var1= ""
  fetch("http://localhost:8080/songs/play2/1")
      .then((data) => data.json())
  .then((data)=> var1=data)

    nino2.innerHTML = nino2.append(var1)}