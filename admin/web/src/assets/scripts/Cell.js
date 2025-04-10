export class Cell {
    constructor(r, c){
        this.r=r;       //行数
        this.c=c;       //列数
        this.x=this.c+0.5;      //在canvas坐标里的x坐标,
        this.y=this.r+0.5;      //在canvas坐标里的y坐标
    }
}