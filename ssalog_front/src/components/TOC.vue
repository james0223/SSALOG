<template>
  <v-card id="documentation-toc" v-scroll="onScroll" app clipped>
    <template v-if="structure !== false">
      <ul class="pt-8 mb-6 documentation-toc">
        <template v-for="(item, i) in internalToc">
          <li
            v-if="item.visible"
            :key="i"
            :class="{
              'documentation-toc__link--subheader': item.subheader,
              'mb-2': i + 1 !== internalToc.length,
              'primary--text': activeIndex === i,
              'text--disabled': activeIndex !== i
            }"
            :style="{
              borderColor: activeIndex === i ? 'currentColor' : undefined
            }"
            class="documentation-toc__link"
          >
            <a
              :href="`#${item.id}`"
              class="d-block font-weight-medium"
              @click.stop.prevent="goTo(`#${item.id}`)"
              v-html="item.text"
            />
          </li>
        </template>
      </ul>
    </template>
  </v-card>
</template>

<script>
// Utilities
export default {
  name: "DocumentToc",
  data: () => ({
    hashTags: false,
    activeIndex: 0,
    currentOffset: 0,
    internalToc: [],
    tocTimeout: 0
  }),
  props: ["htmlData"],
  computed: {
    toc() {
      const hTags = document.querySelectorAll("h1, h2, h3");
      console.log(hTags);
      if (hTags.length >= 2) {
        console.log("로딩됨");
      }
      const t = string => this.$t(`${this.namespace}.${this.page}.${string}`);
      return this.headings
        .map(h => {
          const translation = h.indexOf(".") > -1 ? this.$t(h) : t(h);
          let text = translation.split(" ");
          text.shift();
          text = text.join(" ");
          const isSubheading = translation.substring(0, 3) === "###";
          const isHeading = !isSubheading && translation.substring(0, 2) === "##";
          const isIntroduction = !isHeading && translation.charAt(0) === "#";
          return {
            id: text,
            subheader: isSubheading,
            text,
            visible: isSubheading || isHeading || isIntroduction
          };
        })
        .filter(h => h.visible);
    }
  },
  watch: {
    toc: {
      immediate: true,
      handler(val) {
        if (!val.length) return;
        // eslint-disable-next-line
        this.$nextTick(() => (this.internalToc = this.toc.slice()));
      }
    }
  },
  methods: {
    findActiveIndex() {
      if (this.currentOffset < 100) {
        this.activeIndex = 0;
        return;
      }
      const list = this.toc.slice().reverse();
      const index = list.findIndex(item => {
        const section = document.getElementById(item.id);
        if (!section) return false;
        return section.offsetTop - 100 < this.currentOffset;
      });
      const lastIndex = list.length - 1;
      this.activeIndex = index > -1 ? lastIndex - index : lastIndex;
    },
    onScroll() {
      this.currentOffset = window.pageYOffset || document.documentElement.offsetTop || 0;
      clearTimeout(this.timeout);
      this.timeout = setTimeout(this.findActiveIndex, 50);
    }
  }
};
</script>

<style lang="sass">
#documentation-toc
  .supporter-group__title
    padding-left: 8px
  .documentation-toc
    list-style-type: none !important
    margin: 0
    padding: 32px 0 0
    text-align: left
    width: 100%
    li
      border-left: 2px solid transparent
      padding: 0 24px 0 8px
    li a
      color: inherit
      font-size: .875rem
      font-weight: 400
      text-decoration: none
      transition: color .1s ease-in
    .supporter-group
      justify-content: flex-start !important
    .documentation-toc__link--subheader
      margin-left: 8px
</style>
<!--<template>-->
<!--  <v-container ref="toc" class="TOC" />-->
<!--</template>-->

<!--<script>-->
<!--/* eslint no-use-before-define: 0 */ // &ndash;&gt; OFF-->
<!--export default {-->
<!--  name: "TOC",-->
<!--  mounted() {-->
<!--    const mainContents = document.querySelector(".main_content");-->
<!--    const hTags = mainContents.querySelectorAll("h1, h2, h3");-->
<!--    console.log(hTags);-->
<!--    /* h1, h2, h3 태그가 있는지 확인한다 */-->
<!--    const checkExistenceOfHTags = function() {-->
<!--      if (mainContents === undefined) {-->
<!--        return false;-->
<!--      }-->
<!--      return hTags.length !== 0;-->
<!--    };-->
<!--    checkExistenceOfHTags();-->
<!--  }-->
<!--};-->
<!--</script>-->

<!--<style scoped></style>-->
