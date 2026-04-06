import { useEffect, useState } from "react";
import axios from "axios";

export default function Submissions() {
  const [submissions, setSubmissions] = useState([]);
  const [teams, setTeams] = useState([]);
  const [hackathons, setHackathons] = useState([]);

  const [form, setForm] = useState({
    teamId: "",
    hackathonId: "",
    projectTitle: "",
    githubLink: "",
    description: "",
  });

  const token = localStorage.getItem("token");

  // 🔥 Fetch Hackathons
  const fetchHackathons = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/hackathons");
      setHackathons(res.data);
    } catch (err) {
      console.log(err);
    }
  };

  // 🔥 Fetch Teams
  const fetchTeams = async (hackathonId) => {
    try {
      const res = await axios.get(
        `http://localhost:8080/api/teams/hackathon/${hackathonId}`
      );
      setTeams(res.data);
    } catch (err) {
      console.log(err);
    }
  };

  // 🔥 Fetch Submissions
  const fetchSubmissions = async (hackathonId) => {
    try {
      const res = await axios.get(
        `http://localhost:8080/api/submissions/hackathon/${hackathonId}`
      );
      setSubmissions(res.data);
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    fetchHackathons();
  }, []);

  useEffect(() => {
    if (form.hackathonId) {
      fetchTeams(form.hackathonId);
      fetchSubmissions(form.hackathonId);
    }
  }, [form.hackathonId]);

  // 🔥 Handle Change
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // 🔥 Submit Project
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await axios.post(
        "http://localhost:8080/api/submissions",
        form,
        {
          headers: {
            Authorization: "Bearer " + token,
          },
        }
      );

      alert("Project Submitted 🚀");
      fetchSubmissions(form.hackathonId);
    } catch (err) {
      alert("Error submitting project ❌");
    }
  };

  return (
    <div style={styles.container}>
      <h2>Submissions 📂</h2>

      {/* 🔥 Select Hackathon */}
      <select
        name="hackathonId"
        value={form.hackathonId}
        onChange={handleChange}
        style={styles.input}
      >
        <option value="">Select Hackathon</option>
        {hackathons.map((h) => (
          <option key={h.hackathonId} value={h.hackathonId}>
            {h.name}
          </option>
        ))}
      </select>

      {/* 🔥 Select Team */}
      <select
        name="teamId"
        value={form.teamId}
        onChange={handleChange}
        style={styles.input}
      >
        <option value="">Select Team</option>
        {teams.map((t) => (
          <option key={t.teamId} value={t.teamId}>
            {t.teamName}
          </option>
        ))}
      </select>

      {/* 🔥 Submission Form */}
      <form onSubmit={handleSubmit} style={styles.form}>
        <input
          name="projectTitle"
          placeholder="Project Title"
          onChange={handleChange}
          required
          style={styles.input}
        />

        <input
          name="githubLink"
          placeholder="GitHub Link"
          onChange={handleChange}
          required
          style={styles.input}
        />

        <input
          name="description"
          placeholder="Description"
          onChange={handleChange}
          style={styles.input}
        />

        <button style={styles.button}>Submit</button>
      </form>

      {/* 🔥 Submission List */}
      <div style={styles.list}>
        {submissions.map((s) => (
          <div key={s.submissionId} style={styles.card}>
            <h3>{s.projectTitle}</h3>
            <p>{s.description}</p>
            <a href={s.githubLink} target="_blank" rel="noreferrer">
              View Code 🔗
            </a>
          </div>
        ))}
      </div>
    </div>
  );
}

const styles = {
  container: {
    padding: "20px",
  },
  form: {
    display: "flex",
    gap: "10px",
    flexWrap: "wrap",
    marginTop: "10px",
    marginBottom: "20px",
  },
  input: {
    padding: "10px",
    borderRadius: "6px",
    border: "1px solid #ccc",
  },
  button: {
    padding: "10px 15px",
    background: "#667eea",
    color: "#fff",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer",
  },
  list: {
    display: "flex",
    flexWrap: "wrap",
    gap: "15px",
  },
  card: {
    background: "#fff",
    padding: "15px",
    borderRadius: "10px",
    width: "250px",
    boxShadow: "0 5px 10px rgba(0,0,0,0.1)",
  },
};