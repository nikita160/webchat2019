 function popNotice(elem){
    b = elem.parentNode.children;
    elem.parentNode.insertBefore(elem, b[1]);
 }


 function removeFromSearchList(elem){
     var lia = elem.parentNode;
     lia.removeChild(elem);
     lia.children[0].style.display="block";
     setTimeout (function(){
     lia.parentNode.removeChild(lia);
     }, 3000 );
 }



 function addElemToContactList (name){

        var cont = document.getElementById("contactList");

        var noCnt = document.getElementsByClassName("noContact");
        if (noCnt.length>0){
        noCnt[0].style.display="none";
        }

        toCln = document.getElementsByClassName ("emptyLi")[0];
        cln = toCln.cloneNode(true);
        cln.classList.remove("emptyLi");
        cln.style.display="block";
        t = document.createTextNode(name);
        cln.children[0].children[1].appendChild(t);
        cln.children[0].dataset.contactName =name;
        cont.appendChild(cln);
        return cln;


 }


 function showNotice(msg){
    var from = msg.from;
    //console.log (from);
    var node;
    if (msg.isByFriend){
        alert ("byFriend!" + from)
        var list = document.getElementById("contactList").children;
        for (var i = 0; i<list.length; i++){
            if (list[i].children[0].dataset.contactName == from) {
                node = list[i].children[0];
                break;
            }
        }
    }
    else {
        node = addElemToContactList(from).children[0];
        node.className += " noFriendContact";
        node.dataset.contactName = from;
    }
    alert (node.children[0].children[0]);
    node.children[0].style.display = "block";
    popNotice(node.parentNode);

 }