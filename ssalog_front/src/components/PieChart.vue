<script>
import { Pie, mixins } from "vue-chartjs";

const { reactiveProp } = mixins;
export default {
  mixins: [reactiveProp],
  extends: Pie,
  data() {
    return {
      options: {
        responsive: false,
        maintainAspectRatio: false
      }
    };
  },
  mounted() {
    this.renderChart(this.chartData, this.options);
  }
};
</script>

<style scoped></style>
