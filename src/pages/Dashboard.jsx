import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Dashboard() {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);

  useEffect(() => {
    const storedUser = JSON.parse(localStorage.getItem("user"));
    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/");
    } else {
      setUser(storedUser);
    }
  }, []);

  const handleLogout = () => {
    localStorage.clear();
    navigate("/");
  };

  if (!user) return <h2>Loading...</h2>;

  return (
    <div style={styles.container}>
      
      {/* 🔥 Sidebar */}
      <div style={styles.sidebar}>
        <h2 style={styles.logo}>Hackathon</h2>

        <p style={styles.role}>
          {user.role}
        </p>

        <button onClick={() => navigate("/hackathons")} style={styles.link}>
          Hackathons
        </button>

        <button onClick={() => navigate("/teams")} style={styles.link}>
          Teams
        </button>

        <button onClick={() => navigate("/submissions")} style={styles.link}>
          Submissions
        </button>

        <button onClick={() => navigate("/leaderboard")} style={styles.link}>
          Leaderboard
        </button>

        <button onClick={handleLogout} style={styles.logout}>
          Logout
        </button>
      </div>

      {/* 🔥 Main Content */}
      <div style={styles.main}>
        <h1>Welcome, {user.name} 👋</h1>

        <div style={styles.cards}>

          <div style={styles.card}>
            <h3>Total Hackathons</h3>
            <p>View all hackathons</p>
          </div>

          <div style={styles.card}>
            <h3>Your Role</h3>
            <p>{user.role}</p>
          </div>

          <div style={styles.card}>
            <h3>Submissions</h3>
            <p>Manage your projects</p>
          </div>

        </div>
      </div>
    </div>
  );
}

const styles = {
  container: {
    display: "flex",
    height: "100vh",
  },
  sidebar: {
    width: "220px",
    background: "#1e293b",
    color: "#fff",
    padding: "20px",
    display: "flex",
    flexDirection: "column",
    gap: "10px",
  },
  logo: {
    marginBottom: "20px",
  },
  role: {
    fontSize: "14px",
    marginBottom: "20px",
    color: "#94a3b8",
  },
  link: {
    padding: "10px",
    background: "transparent",
    color: "#fff",
    border: "none",
    textAlign: "left",
    cursor: "pointer",
  },
  logout: {
    marginTop: "auto",
    padding: "10px",
    background: "#ef4444",
    color: "#fff",
    border: "none",
    cursor: "pointer",
  },
  main: {
    flex: 1,
    padding: "30px",
    background: "#f1f5f9",
  },
  cards: {
    display: "flex",
    gap: "20px",
    marginTop: "20px",
  },
  card: {
    background: "#fff",
    padding: "20px",
    borderRadius: "10px",
    width: "200px",
    boxShadow: "0 5px 15px rgba(0,0,0,0.1)",
  },
};