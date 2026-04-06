import { useEffect, useState } from "react";
import axios from "axios";

export default function Teams() {
  const [teams, setTeams] = useState([]);
  const [hackathons, setHackathons] = useState([]);
  const [form, setForm] = useState({
    teamName: "",
    hackathonId: "",
  });

  const token = localStorage.getItem("token");

  // 🔥 Fetch Hackathons (for dropdown)
  const fetchHackathons = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/hackathons");
      setHackathons(res.data);
    } catch (err) {
      console.log(err);
    }
  };

  // 🔥 Fetch Teams
  const fetchTeams = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/teams/hackathon/" + form.hackathonId);
      setTeams(res.data);
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    fetchHackathons();
  }, []);

  useEffect(() => {
    if (form.hackathonId) {
      fetchTeams();
    }
  }, [form.hackathonId]);

  // 🔥 Handle Change
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // 🔥 Create Team
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await axios.post(
        "http://localhost:8080/api/teams",
        form,
        {
          headers: {
            Authorization: "Bearer " + token,
          },
        }
      );

      alert("Team Created ✅");
      fetchTeams();
    } catch (err) {
      alert("Error creating team ❌");
    }
  };

  return (
    <div style={styles.container}>
      <h2>Teams 👥</h2>

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

      {/* 🔥 Create Team */}
      <form onSubmit={handleSubmit} style={styles.form}>
        <input
          name="teamName"
          placeholder="Team Name"
          onChange={handleChange}
          required
          style={styles.input}
        />

        <button style={styles.button}>Create Team</button>
      </form>

      {/* 🔥 Team List */}
      <div style={styles.list}>
        {teams.map((team) => (
          <div key={team.teamId} style={styles.card}>
            <h3>{team.teamName}</h3>
            <p>Hackathon ID: {team.hackathonId}</p>
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
    width: "220px",
    boxShadow: "0 5px 10px rgba(0,0,0,0.1)",
  },
};