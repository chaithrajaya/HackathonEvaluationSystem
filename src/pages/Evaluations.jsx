import { useState } from "react";
import API from "../api/axios";

export default function Evaluation() {
  const [data, setData] = useState({
    submissionId: "",
    judgeUserId: "",
    score: "",
    feedback: ""
  });

  const evaluate = async () => {
    await API.post("/evaluations", data);
    alert("Evaluation Added");
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Evaluate Submission</h2>

      <input placeholder="Submission ID" onChange={(e)=>setData({...data,submissionId:e.target.value})}/>
      <input placeholder="Judge ID" onChange={(e)=>setData({...data,judgeUserId:e.target.value})}/>
      <input placeholder="Score" onChange={(e)=>setData({...data,score:e.target.value})}/>
      <input placeholder="Feedback" onChange={(e)=>setData({...data,feedback:e.target.value})}/>

      <button onClick={evaluate}>Submit</button>
    </div>
  );
}