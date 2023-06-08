function fillAdminURI(){
    const adminPort = 8050;
    const adminPref = "admin";

    const currentURI = window.location;
    document.getElementById("adminEnter").href = currentURI.protocol + "//" + currentURI.hostname + ":" + adminPort + "/" + adminPref;
}

fillAdminURI();

