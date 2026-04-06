import { Link, useNavigate } from "react-router-dom";

export default function Navbar() {
  const nav = useNavigate();

  const logout = () => {
    localStorage.removeItem("token");
    nav("/");
  };

  return (
    <div style={{background:"#333", padding:"10px"}}>
      <Link to="/dashboard" style={{color:"white", margin:"10px"}}>Dashboard</Link>
      <Link to="/hackathons" style={{color:"white", margin:"10px"}}>Hackathons</Link>
      <button onClick={logout}>Logout</button>
    </div>
  );
}