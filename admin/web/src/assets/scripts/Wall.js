import { AcGameObjects } from "./AcGameObject";

export class Wall extends AcGameObjects{
    constructor(r, c, gamemap){
        super();

        this.c=c;       //障碍物在第几列
        this.r=r;       //障碍物在第几行
        this.gamemap=gamemap;       //用来索引之前创建的GameMap对象
        this.color="#F6B78C";       //障碍物颜色
        //粉#F69A8B
    }

    start(){

    }

    update(){
        this.render();
    }

    render(){
        const L=this.gamemap.L;
        const ctx=this.gamemap.ctx;

        // console.log(L , this.c*L);
        ctx.fillStyle=this.color;
        ctx.fillRect(L * this.c, L * this.r, L , L);

    }
}