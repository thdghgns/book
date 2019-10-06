<template>
  <div>
    <b-button size="sm" v-b-modal.sign-in-modal>Sign In</b-button>

    <b-modal
      id="sign-in-modal"
      ref="modal"
      title="Sign In"
      @show="resetModal"
      @hidden="resetModal"
      @ok="handleSignIn"
    >
      <form ref="form" @submit.stop.prevent="signIn()">
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
  name: 'login_modal',
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
    handleSignIn (bvModalEvt) {
      bvModalEvt.preventDefault()
      this.signIn()
    },
    signIn () {
      let form = new FormData()
      form.set('username', this.username)
      form.set('password', this.password)

      this.$Axios.post('http://localhost:8080/login', form)
        .then(response => {
          console.log(response)
          localStorage.setItem('username', this.username)
          localStorage.setItem('authenticated', 'true')

          this.$nextTick(() => {
            this.$EventBus.$emit('signedIn', this.username)
            this.$refs.modal.hide()
          })
        })
        .catch(error => {
          if (error.response) {
            alert('로그인 실패')
            console.log(error.response.data)
            console.log(error.response.status)
            console.log(error.response.headers)
          }
        })
    }
  }

}
</script>
