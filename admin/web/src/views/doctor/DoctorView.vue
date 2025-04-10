<template>
  <div>
    <div
      class="modal modal-dialog-scrollable"
      id="exampleModal1"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">
              输入医生的信息
            </h1>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <form>
              <div class="mb-3">
                <label for="name" class="col-form-label">姓名</label>
                <input
                  type="text"
                  v-model="form_data.name"
                  class="form-control"
                  id="name"
                />
              </div>
              <div class="mb-3">
                <label for="age" class="col-form-label">年龄</label>
                <input
                  type="text"
                  v-model="form_data.age"
                  class="form-control"
                  id="age"
                />
              </div>
              <div class="mb-3">
                <label for="phone" class="col-form-label">电话</label>
                <input
                  type="text"
                  v-model="form_data.phone"
                  class="form-control"
                  id="phone"
                />
              </div>
              <div class="mb-3">
                <label for="avatar" class="col-form-label">头像</label>
                <input
                  type="text"
                  v-model="form_data.avatar"
                  class="form-control"
                  id="avatar"
                />
              </div>
              <div class="mb-3">
                <label for="workplace" class="col-form-label"
                  >工作医院/位置</label
                >
                <input
                  type="text"
                  v-model="form_data.workplace"
                  class="form-control"
                  id="workplace"
                />
              </div>
              <div class="mb-3">
                <label for="department" class="col-form-label"
                  >选择科室</label
                >
                <select
                  class="form-select"
                  v-model="form_data.deptId"
                  aria-label="Default select example"
                >
                  <option selected value="1">儿科</option>
                  <option value="2">皮肤科</option>
                  <option value="3">妇科</option>
                  <option value="4">男科</option>
                  <option value="5">消化科</option>
                </select>
              </div>

              <div class="mb-3">
                <label for="sickness" class="col-form-label"
                  >选择擅长疾病 (选择1~3个)</label
                >
                <div style="display: flex">
                  <select
                    class="form-select"
                    style="width: 33%"
                    v-model="form_data.sicknessId[0]"
                    aria-label="Default select example"
                  >
                    <option value="1">小儿腹泻</option>
                    <option value="2">小儿感冒</option>
                    <option value="3">小儿便秘</option>
                    <option value="4">痘痘</option>
                    <option value="5">荨麻疹</option>
                    <option value="6">皮炎</option>
                    <option value="7">月经不调</option>
                    <option value="8">痛经</option>
                    <option value="9">阴道炎</option>
                    <option value="10">前列腺炎</option>
                    <option value="11">早泄</option>
                    <option value="12">附睾炎</option>
                    <option value="13">胃炎</option>
                    <option value="14">胃溃疡</option>
                    <option value="15">便秘</option>
                  </select>

                  <select
                    class="form-select"
                    style="width: 33%"
                    v-model="form_data.sicknessId[1]"
                    aria-label="Default select example"
                  >
                    <option value="1">小儿腹泻</option>
                    <option value="2">小儿感冒</option>
                    <option value="3">小儿便秘</option>
                    <option value="4">痘痘</option>
                    <option value="5">荨麻疹</option>
                    <option value="6">皮炎</option>
                    <option value="7">月经不调</option>
                    <option value="8">痛经</option>
                    <option value="9">阴道炎</option>
                    <option value="10">前列腺炎</option>
                    <option value="11">早泄</option>
                    <option value="12">附睾炎</option>
                    <option value="13">胃炎</option>
                    <option value="14">胃溃疡</option>
                    <option value="15">便秘</option>
                  </select>

                  <select
                    class="form-select"
                    style="width: 33%"
                    v-model="form_data.sicknessId[2]"
                    aria-label="Default select example"
                  >
                    <option value="1">小儿腹泻</option>
                    <option value="2">小儿感冒</option>
                    <option value="3">小儿便秘</option>
                    <option value="4">痘痘</option>
                    <option value="5">荨麻疹</option>
                    <option value="6">皮炎</option>
                    <option value="7">月经不调</option>
                    <option value="8">痛经</option>
                    <option value="9">阴道炎</option>
                    <option value="10">前列腺炎</option>
                    <option value="11">早泄</option>
                    <option value="12">附睾炎</option>
                    <option value="13">胃炎</option>
                    <option value="14">胃溃疡</option>
                    <option value="15">便秘</option>
                  </select>
                </div>
              </div>

              <div class="mb-3">
                <label for="level" class="col-form-label">职级</label>
                <input
                  type="text"
                  v-model="form_data.level"
                  class="form-control"
                  id="level"
                />
              </div>

              <div class="mb-3">
                <label for="message-text" class="col-form-label">描述:</label>
                <textarea
                  v-model="form_data.desc"
                  class="form-control"
                  id="message-text"
                ></textarea>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              退出
            </button>
            <button
              type="button"
              class="btn btn-primary"
              data-bs-dismiss="modal"
              @click="onAdd"
            >
              保存提交
            </button>
          </div>
        </div>
      </div>
    </div>


    <div
      class="modal modal-dialog-scrollable"
      id="exampleModal2"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">
              输入医生的信息
            </h1>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <form>
              <div class="mb-3">
                <label for="name" class="col-form-label">姓名</label>
                <input
                  type="text"
                  v-model="form_data.name"
                  class="form-control"
                  id="name"
                />
              </div>
              <div class="mb-3">
                <label for="age" class="col-form-label">年龄</label>
                <input
                  type="text"
                  v-model="form_data.age"
                  class="form-control"
                  id="age"
                />
              </div>
              <div class="mb-3">
                <label for="phone" class="col-form-label">电话</label>
                <input
                  type="text"
                  v-model="form_data.phone"
                  class="form-control"
                  id="phone"
                />
              </div>
              <div class="mb-3">
                <label for="avatar" class="col-form-label">头像</label>
                <input
                  type="text"
                  v-model="form_data.avatar"
                  class="form-control"
                  id="avatar"
                />
              </div>
              <div class="mb-3">
                <label for="workplace" class="col-form-label"
                  >工作医院/位置</label
                >
                <input
                  type="text"
                  v-model="form_data.workplace"
                  class="form-control"
                  id="workplace"
                />
              </div>
              <div class="mb-3">
                <label for="department" class="col-form-label"
                  >选择科室</label
                >
                <select
                  class="form-select"
                  v-model="form_data.deptId"
                  aria-label="Default select example"
                >
                  <option selected value="1">儿科</option>
                  <option value="2">皮肤科</option>
                  <option value="3">妇科</option>
                  <option value="4">男科</option>
                  <option value="5">消化科</option>
                </select>
              </div>

              <div class="mb-3">
                <label for="sickness" class="col-form-label"
                  >选择擅长疾病 (选择1~3个)</label
                >
                <div style="display: flex">
                  <select
                    class="form-select"
                    style="width: 33%"
                    v-model="form_data.sicknessId[0]"
                    aria-label="Default select example"
                  >
                    <option value="1">小儿腹泻</option>
                    <option value="2">小儿感冒</option>
                    <option value="3">小儿便秘</option>
                    <option value="4">痘痘</option>
                    <option value="5">荨麻疹</option>
                    <option value="6">皮炎</option>
                    <option value="7">月经不调</option>
                    <option value="8">痛经</option>
                    <option value="9">阴道炎</option>
                    <option value="10">前列腺炎</option>
                    <option value="11">早泄</option>
                    <option value="12">附睾炎</option>
                    <option value="13">胃炎</option>
                    <option value="14">胃溃疡</option>
                    <option value="15">便秘</option>
                  </select>

                  <select
                    class="form-select"
                    style="width: 33%"
                    v-model="form_data.sicknessId[1]"
                    aria-label="Default select example"
                  >
                    <option value="1">小儿腹泻</option>
                    <option value="2">小儿感冒</option>
                    <option value="3">小儿便秘</option>
                    <option value="4">痘痘</option>
                    <option value="5">荨麻疹</option>
                    <option value="6">皮炎</option>
                    <option value="7">月经不调</option>
                    <option value="8">痛经</option>
                    <option value="9">阴道炎</option>
                    <option value="10">前列腺炎</option>
                    <option value="11">早泄</option>
                    <option value="12">附睾炎</option>
                    <option value="13">胃炎</option>
                    <option value="14">胃溃疡</option>
                    <option value="15">便秘</option>
                  </select>

                  <select
                    class="form-select"
                    style="width: 33%"
                    v-model="form_data.sicknessId[2]"
                    aria-label="Default select example"
                  >
                    <option value="1">小儿腹泻</option>
                    <option value="2">小儿感冒</option>
                    <option value="3">小儿便秘</option>
                    <option value="4">痘痘</option>
                    <option value="5">荨麻疹</option>
                    <option value="6">皮炎</option>
                    <option value="7">月经不调</option>
                    <option value="8">痛经</option>
                    <option value="9">阴道炎</option>
                    <option value="10">前列腺炎</option>
                    <option value="11">早泄</option>
                    <option value="12">附睾炎</option>
                    <option value="13">胃炎</option>
                    <option value="14">胃溃疡</option>
                    <option value="15">便秘</option>
                  </select>
                </div>
              </div>

              <div class="mb-3">
                <label for="level" class="col-form-label">职级</label>
                <input
                  type="text"
                  v-model="form_data.level"
                  class="form-control"
                  id="level"
                />
              </div>

              <div class="mb-3">
                <label for="message-text" class="col-form-label">描述:</label>
                <textarea
                  v-model="form_data.desc"
                  class="form-control"
                  id="message-text"
                ></textarea>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              退出
            </button>
            <button
              type="button"
              class="btn btn-primary"
              data-bs-dismiss="modal"
              @click="submitUpdate()"
            >
              更新
            </button>
          </div>
        </div>
      </div>
    </div>


    <ContentField>
      <div class="box">
        <div class="box-left">
          <div class="input-group mb-3">
            <input
              type="text"
              class="my-input"
              placeholder="输入医生姓名进行查找"
              aria-label="Recipient's username"
              aria-describedby="button-addon2"
              v-model="input_msg"
            />
            <button
              class="btn btn-outline-secondary"
              type="button"
              id="button-addon2"
              @click="onClick()"
            >
              搜索
            </button>
          </div>
        </div>
        <div class="box-right">
          <button
            type="button"
            class="btn btn-primary"
            data-bs-toggle="modal"
            data-bs-target="#exampleModal1"
            data-bs-whatever="@mdo"
            @click="beforeAdd()"
          >
            + 添加医生
          </button>
        </div>
      </div>
    </ContentField>

    <ContentField>
      <table class="table">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">姓名</th>
            <th scope="col">年龄</th>
            <th scope="col">电话</th>
            <th scope="col">工作地点</th>
            <th scope="col">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in doctorList" :key="item.id">
            <th scope="row">{{ item.id }}</th>
            <td>{{ item.name }}</td>
            <td>{{ item.age }}</td>
            <td>{{ item.phone }}</td>
            <td>{{ item.workplace }}</td>
            <td>
              <div>
                <span style="color: #00bc00" @click="getById(item.id)">
                    <button
                        type="button"
                        class="btn btn-primary"
                        data-bs-toggle="modal"
                        data-bs-target="#exampleModal2"
                        data-bs-whatever="@mdo"
                    >
                        修改
                    </button>    
                </span> &nbsp;|&nbsp;
                <span style="color: #e6eef7">详情</span> &nbsp;| &nbsp;
                <span style="color: #fd3333" @click="onDelete(item.id)"
                  >删除</span
                >
              </div>
            </td>
          </tr>
        </tbody>
      </table>
      <nav aria-label="...">
        <ul class="pagination" style="float: right">
          <li class="page-item" @click="click_page(-2)">
            <a class="page-link" href="#">前一页</a>
          </li>
          <li
            :class="'page-item ' + page.is_active"
            v-for="page in pages"
            :key="page.number"
            @click="click_page(page.number)"
          >
            <a class="page-link" href="#">{{ page.number }}</a>
          </li>

          <li class="page-item" @click="click_page(-1)">
            <a class="page-link" href="#">后一页</a>
          </li>
        </ul>
      </nav>
    </ContentField>
  </div>
</template>

<script>
import ContentField from "@/components/ContentField.vue";
import $ from "jquery";
import { ref } from "vue";
export default {
  components: {
    // 注册组件
    ContentField,
  },
  setup() {
    let input_msg = ref("");
    let now_page = ref(1); //当前页码
    let doctorList = ref([]); //医生列表
    let total = ref(0); //总条数
    let pages = ref([]); //分页
    let form_data = ref({   //添加医生时，封装的数据
        id:null,
        name: null,
        age: null,
        phone: null,
        avatar: null,
        workplace: null,
        level: null,
        desc: null,
        deptId: null,
        sicknessId: ['','',''],
    });

    //添加医生之前先把，表单数据清空（预处理）
    const beforeAdd=()=>{
      form_data.value.id=null;
      form_data.value.name=null;
      form_data.value.age=null;
      form_data.value.phone=null;
      form_data.value.avatar=null;
      form_data.value.workplace=null;
      form_data.value.level=null;
      form_data.value.desc=null;
      form_data.value.deptId=null;
      form_data.value.sicknessId=['','',''];
    }

    //添加医生
    const onAdd = () => {
        console.log(form_data.value);
        check();
        
        $.ajax({
            url: "http://localhost:8080/api/admin/doctor/insert",
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(form_data.value),
            headers: {
            authorization: localStorage.getItem("token"),
            },
            success: (res) => {
                if (res.code == 1) {
                    console.log(res);
                    form_data.value.id=null;
                    form_data.value.name=null;
                    form_data.value.age=null;
                    form_data.value.phone=null;
                    form_data.value.avatar=null;
                    form_data.value.workplace=null;
                    form_data.value.level=null;
                    form_data.value.desc=null;
                    form_data.value.deptId=null;
                    form_data.value.sicknessId=['','',''];
            
                }
            },
        });
    };

    //确定将要添加的疾病id，以及转成整数  
    const check=()=>{
        let arr= [];
        form_data.value.sicknessId.forEach((item)=>{
            if(item!==''){
                arr.push(parseInt(item));
            }
        })
        form_data.value.sicknessId=arr;
    }

    //删除医生
    const onDelete = (id) => {
      $.ajax({
        url: "http://localhost:8080/api/admin/doctor/delete",
        type: "POST",
        data: {
          id: id,
        },
        headers: {
          authorization: localStorage.getItem("token"),
        },
        success: (res) => {
          if (res.code == 1) {
            console.log(res);
            click_page(now_page.value);
          }
        },
      });
    };

    //根据 id 查询医生全部信息
    const getById=(id)=>{
        $.ajax({
            url:"http://localhost:8080/api/admin/doctor/select",
            type:"GET",
            data:{
                id : id
            },
            headers:{
                authorization: localStorage.getItem("token"),
            }, 
            success:(res)=>{
                if(res.code == 1){
                    console.log(res);
                    form_data.value.name=res.data.name;
                    form_data.value.age=res.data.age;
                    form_data.value.phone=res.data.phone;
                    form_data.value.avatar=res.data.avatar;
                    form_data.value.workplace=res.data.workplace;
                    form_data.value.level=res.data.level;
                    form_data.value.desc=res.data.desc;
                    // form_data.value.deptId=1;
                    // form_data.value.sicknessId=['','',''];
                    form_data.value.id=id;
                }
            }
        });
    }


    //根据 id 更新医生信息
    const submitUpdate=()=>{
        check();
        console.log(form_data.value);
        $.ajax({
            url: 'http://localhost:8080/api/admin/doctor/update',
            type:'POST',
            headers:{
                authorization: localStorage.getItem("token"),
            },
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(form_data.value),
            success:(res)=>{
                if(res.code ==1){
                    console.log(res);
                    
                    form_data.value.id=null;
                    form_data.value.name=null;
                    form_data.value.age=null;
                    form_data.value.phone=null;
                    form_data.value.avatar=null;
                    form_data.value.workplace=null;
                    form_data.value.level=null;
                    form_data.value.desc=null;
                    form_data.value.deptId=null;
                    form_data.value.sicknessId=['','',''];
                }
            }
        })
    }

    //点击搜索按钮
    const onClick = () => {
      getDoctorList(1, input_msg.value);
    };

    const update_pages = () => {
      let max_pages = parseInt(Math.ceil(total.value / 10));
      let new_pages = [];
      for (let i = now_page.value - 2; i <= now_page.value + 2; i++) {
        if (i >= 1 && i <= max_pages) {
          new_pages.push({
            number: i,
            is_active: i === now_page.value ? "active" : "",
          });
        }
      }
      pages.value = new_pages;
      console.log(pages.value);
    };


    const click_page = (page) => {
      if (page === -2) page = now_page.value - 1;
      else if (page === -1) page = now_page.value + 1;
      let max_pages = parseInt(Math.ceil(total.value / 10));
      if (page >= 1 && page <= max_pages) {
        getDoctorList(page, "");
      }
    };

    //获取医生列表
    const getDoctorList = (page, name) => {
      now_page.value = page;
      $.ajax({
        url: "http://localhost:8080/api/admin/doctor/page",
        type: "GET",
        data: {
          name: name,
          page: page,
          pageSize: 10,
        },
        headers: {
          authorization: localStorage.getItem("token"),
        },
        success: (res) => {
          if (res.code == 1) {
            console.log(res);
            doctorList.value = res.data.items; //更新医生列表
            total.value = res.data.total; //更新总条数
            update_pages(); //更新分页
          }
        },
      });
    };

    getDoctorList(now_page.value, ""); //查询第一页

    return {
      doctorList,
      pages,
      click_page,
      input_msg,
      onClick,
      onAdd,
      form_data,
      onDelete,
      getById,
      submitUpdate,
      beforeAdd
    };
  },
};
</script>

<style scoped>
.box {
  display: flex;
}
.box-left {
  width: 50%;
}
.box-right {
  width: 50%;
  text-align: right;
}

.my-input {
  width: 20vw;
}
</style>
