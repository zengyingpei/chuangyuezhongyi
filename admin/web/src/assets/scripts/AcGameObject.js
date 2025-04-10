let AC_GAME_OBJECTS = [];

export class AcGameObjects {
    constructor() {
        AC_GAME_OBJECTS.push(this);

        this.timedelta = 0;              //当前这一帧 距离上一帧 的时间间隔
        this.has_call_start = false;    //表示有没有执行过start
    }

    start() {    //初始执行一次

    }

    update() {    //每一帧执行一次

    }

    on_destroy(){

    }

    destory() {     //删除当前对象
        this.on_destroy();

        for (let i in AC_GAME_OBJECTS) {
            if (AC_GAME_OBJECTS[i] === this) {
                AC_GAME_OBJECTS.splice(i, 1);
                break;
            }
        }
    }
}


let last_timestamp;    //上一次执行时刻
let AC_GAME_OBJECTS_FRAME = function (timestamp) {    //timestamp是当前执行时刻
    for (let obj of AC_GAME_OBJECTS) {
        if (!obj.has_call_start) {
            obj.start();
            obj.has_call_start = true;
        }
        else {
            obj.timedelta = timestamp - last_timestamp;           //更新时间间隔
            obj.update();
        }
    }
    last_timestamp = timestamp;
    requestAnimationFrame(AC_GAME_OBJECTS_FRAME);   //递归执行
}

requestAnimationFrame(AC_GAME_OBJECTS_FRAME);   //动画函数启动
