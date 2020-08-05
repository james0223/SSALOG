<template>
  <div>
    <v-row align="center">
      <v-col class="d-flex" cols="2">
        <v-select v-model="SelectedCategory" :items="category" label="검색유형"></v-select>
      </v-col>
      <v-col class="d-flex" cols="8">
        <v-menu offset-y>
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
              autofocus
              :readonly="SelectedCategory === '문제유형'"
              v-bind="SelectedCategory === '문제유형' ? attrs : null"
              v-on="SelectedCategory === '문제유형' ? on : null"
              v-model="q"
              label="검색어를 입력해주세요"
              append-icon="mdi-magnify"
              @keypress.enter="goSearch"
            ></v-text-field>
          </template>
          <v-card>
            <v-card-text>
              <v-chip-group v-model="amenities" column multiple>
                <v-chip
                  v-for="(catagory, i) in $store.state.ProblemCategory"
                  :key="i"
                  filter
                  outlined
                  >{{ catagory.kor }}</v-chip
                >
              </v-chip-group>
            </v-card-text>
          </v-card>
        </v-menu>
      </v-col>
      <v-col clos="1">
        <v-btn tile block color="primary" @click="goSearch">검색</v-btn>
      </v-col>
    </v-row>
  </div>
</template>

<script>
export default {
  name: "SearchBar",
  data() {
    return {
      category: ["문제번호", "문제제목", "문제유형", "회원"],
      q: null,
      SelectedCategory: null
    };
  },
  methods: {
    goSearch() {
      if (this.category.indexOf(this.SelectedCategory) === 3) {
        this.$router.push({ name: "AccountList", query: { q: this.q } });
      } else {
        this.$router.push({
          name: "ProblemList",
          query: {
            q: this.q,
            categoryIdx: this.category.indexOf(this.SelectedCategory)
          }
        });
      }
    }
  },
  props: {
    SelectedCategoryIdx: Number
  },
  mounted() {
    this.SelectedCategory = this.category[this.SelectedCategoryIdx];
  }
};
</script>

<style></style>
