// The local static server uses the separate Spring Boot port. In production,
// the frontend is bundled into Spring Boot and calls the API on the same origin.
const isLocalFrontendServer =
  ["localhost", "127.0.0.1"].includes(window.location.hostname) &&
  window.location.port === "5500";

window.SCHOLARSHIP_API_BASE = isLocalFrontendServer
  ? "http://localhost:8080/api"
  : `${window.location.origin}/api`;
