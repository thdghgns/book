<template>
  <div>
    <b-tabs
      active-nav-item-class="font-weight-bold text-uppercase text-danger"
      active-tab-class="font-weight-bold text-success"
      content-class="mt-3"
    >
      <b-tab title="Popular" active>
        <b-table
          id="rank-table" striped hover
          :items="rItems"
          :fields="rFields">
        </b-table>
      </b-tab>
      <b-tab title="My History" v-if="this.authenticated === 'true'">
        <b-table
          id="history-table" striped hover
          :items="hItems"
          :fields="hFields">
        </b-table>
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
export default {
  name: 'BookSearchHistory',
  data () {
    return {
      authenticated: 'false',
      rItems: [],
      rFields: ['keyword', 'count'],
      hItems: [],
      hFields: ['keyword', 'createdDate']
    }
  },
  methods: {
    signIn (username) {
      this.authenticated = 'true'
    },
    signOut (authenticated) {
      this.authenticated = 'false'
    },
    refreshRank () {
      let axiosConfig = {
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        }
      }

      this.$Axios.get('http://localhost:8080/books/top10Keyword', axiosConfig)
        .then(response => {
          this.rItems = response.data
        })
        .catch(error => {
          if (error.response) {
            console.log(error.response.data)
            console.log(error.response.status)
            console.log(error.response.headers)
          }
        })
    },
    refreshHistory () {
      if (this.authenticated === 'true') {
        let axiosConfig = {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8'
          }
        }

        this.$Axios.get('http://localhost:8080/user/history', axiosConfig)
          .then(response => {
            this.hItems = response.data
          })
          .catch(error => {
            if (error.response) {
              console.log(error.response.data)
              console.log(error.response.status)
              console.log(error.response.headers)
            }
          })
      }
    }
  },
  mounted () {
    this.$EventBus.$on('signedIn', this.signIn)
    this.$EventBus.$on('signedOut', this.signOut)
    this.$EventBus.$on('refreshRank', this.refreshRank)
    this.$EventBus.$on('refreshHistory', this.refreshHistory)
    if (localStorage.authenticated) {
      this.authenticated = localStorage.authenticated
    }
  }
}
</script>

<style scoped>

</style>
