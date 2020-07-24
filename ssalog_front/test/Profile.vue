<template>
<div>
  <input ref="imageInput" type="file" accept="image/png, image/jpeg, image/jpg" hidden @change="onChangeImages" />
  <v-btn type="button" @click="onClickImageUpload">이미지 업로드</v-btn>
  <v-img class="imgbox" v-if="imageUrl" :src="imageUrl"></v-img>
</div>
</template>

<script>
import axios from "axios";
const ai = axios.create({
    baseURL: "http://localhost:8080"
  //baseURL: "http://i3b101.p.ssafy.io:8080"
});

export default {
  data() {
    return {
      imageUrl: null,
      imageFile : null
    };
  },
  methods: {
    onClickImageUpload() {
      this.$refs.imageInput.click();
    },
    onChangeImages(e) {
      console.log(e.target.files);
      const file = e.target.files[0];
      this.imageUrl = URL.createObjectURL(file);
      this.imageFile = file;


        const formData = new FormData();
        formData.append('filename',this.imageFile);
        formData.append('username',"gofeel8");   //<--- gofeel8 말고 사용자의 usernae(ID)를 넣어줘야 한다
      ai.post("/newuser/upload_img", formData,{
        headers:{
            'Content-Type' : 'multipart/form-data'
        }
      })
        .then(res => {
          console.dir(res.data);
          
        })
        .catch(e => {
          alert(e);
        });
        
    },
    submit(){
        const formData = new FormData();
        formData.append('filename',this.imageFile);
        formData.append('username',"gofeel8");
      ai.post("/newuser/upload_img", formData,{
        headers:{
            'Content-Type' : 'multipart/form-data'
        }
      })
        .then(res => {
          console.dir(res.data);
          
        })
        .catch(e => {
          alert(e);
        });
    }

  }
};
</script>

<style lang="scss" scoped>
.imgbox {
      display: block;
  margin-left: auto;
  margin-right: auto;
width: auto; height: auto;
    max-width: 200px;
    max-height: 2   00px;
    border-radius: 50%;
}
</style>
