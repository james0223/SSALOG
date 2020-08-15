import axios from "axios";
import store from "@/store";
/*eslint-disable */
export default async function getSSALOG(pageId) {
  try {
    let res = await axios.get(`${store.state.ServerURL}/newuser/post/import_view`, {
      params: {
        Scoring: pageId
      }
    });
    console.log(res);
    return Promise.resolve(res.data);
  } catch (e) {
    console.error(e);
  }
}
