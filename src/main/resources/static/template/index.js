let id_input = document.getElementById("addId");
let submit = document.getElementById("submit")


submit.addEventListener("click", () => {
    let content = id_input.value;
    let reg = /^\d+$/;
    let res = reg.test(content);
    if (content.length < 16) {
        alert("ID至少为16位");
        return;
    }
    if (res === false) {
        alert("ID只能包含数字");
        return;
    }
    let url = `https://www.yiyu951.xyz/device/addDeviceToMap?device_id=${content}`;
    fetch(url, {
        method : "get"
    }).then(res => {
        return res.json();
    }).then(res => {
        console.log(res);
        if (res === true) {
            alert("添加ID成功!");
        }
        else {
            alert("添加ID失败,请检查ID是否重复");
        }
    })
})
