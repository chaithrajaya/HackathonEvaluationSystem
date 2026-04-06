import { useEffect, useState } from "react";
import axios from "axios";

export default function Hackathons() {
  const [hackathons, setHackathons] = useState([]);
  const [form, setForm] = useState({
    name: "",
    description: "",
    startDate: "",
    endDate: "",
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

  useEffect(() => {
    fetchHackathons();
  }, []);

  // 🔥 Handle Form Change
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // 🔥 Create Hackathon
  const handleSubmit = async (e) => {
    e.preventDefault();

    const user = JSON.parse(localStorage.getItem("user"));

    try {
      await axios.post(
        "http://localhost:8080/api/hackathons",
        {
          ...form,
          createdByUserId: user.userId,
        },
        {
          headers: {
            Authorization: "Bearer " + token,
          },
        }
      );

      alert("Hackathon Created ✅");
      fetchHackathons();
    } catch (err) {
      alert("Error creating hackathon ❌");
    }
  };

  return (
    <div style={styles.container}>
      <h2>Hackathons 🚀</h2>

      {/* 🔥 Create Form */}
      <form onSubmit={handleSubmit} style={styles.form}>
        <input
          name="name"
          placeholder="Hackathon Name"
          onChange={handleChange}
          required
          style={styles.input}
        />

        <input
          name="description"
          placeholder="Description"
          onChange={handleChange}
          required
          style={styles.input}
        />

        <input
          type="datetime-local"
          name="startDate"
          onChange={handleChange}
          required
          style={styles.input}
        />

        <input
          type="datetime-local"
          name="endDate"
          onChange={handleChange}
          required
          style={styles.input}
        />

        <button style={styles.button}>Create</button>
      </form>

      {/* 🔥 Hackathon List */}
      <div style={styles.list}>
        {hackathons.map((h) => (
          <div key={h.hackathonId} style={styles.card}>
            <h3>{h.name}</h3>
            <p>{h.description}</p>
            <p>
              {new Date(h.startDate).toLocaleString()} →{" "}
              {new Date(h.endDate).toLocaleString()}
            </p>
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