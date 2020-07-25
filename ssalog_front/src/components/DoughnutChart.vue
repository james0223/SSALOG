<script>
import { Doughnut } from "vue-chartjs";

export default {
  extends: Doughnut,
  props: ["chartData"],

  mounted() {
    this.renderChart(this.chartData);
  }
};
</script>
