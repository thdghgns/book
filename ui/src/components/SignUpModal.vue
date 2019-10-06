<template>
  <div>
    <b-button size="sm" v-b-modal.sign-up-modal>Sign Up</b-button>

    <b-modal
      id="sign-up-modal"
      ref="modal"
      title="Sign Up"
      @show="resetModal"
      @hidden="resetModal"
      @ok="handleSignUp"
    >
      <form ref="form" @submit.stop.prevent="signUp()">
        <b-form-group
          id="username-input"
          label="Username:"
          label-for="username"
        >
          <b-form-input
            id="username"
            v-model="username"
            required
            placeholder="username"
          ></b-form-input>
        </b-form-group>

        <b-form-group id="password-input" label="Password:" label-for="password">
          <b-form-input
            id="password"
            v-model="password"
            type="password"
            required
            placeholder="password"
          ></b-form-input>
        </b-form-group>
      </form>
    </b-modal>
  </div>
</template>

<script>
export default {
  name: 'sign_up_modal',
  data () {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    resetModal () {
      this.username = ''
      this.password = ''
    },
    handleSignUp (bvModalEvt) {
      bvModalEvt.preventDefault()
      this.signUp()
    },
    signUp () {
      let postData = {
        username: this.username,
        password: this.password
      }

      var axiosConfig = {
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        }
      }

      this.$Axios.post('http://localhost:8080/user', postData, axiosConfig)
        .then(response => {
          console.log(response)
          alert('회원가입 성공')
          this.$nextTick(() => {
            this.$refs.modal.hide()
          })
        })
        .catch(error => {
          if (error.response) {
            alert('회원가입 실패')
            console.log(error.response.data)
            console.log(error.response.status)
            console.log(error.response.headers)
          }
        })
    }
  }
}
</script>
