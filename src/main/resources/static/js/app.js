document.addEventListener("DOMContentLoaded", () => {
  const dateInput = document.getElementById("date");
  if (dateInput) {
    const now = new Date();
    const localDate = new Date(now.getTime() - now.getTimezoneOffset() * 60000).toISOString().slice(0, 10);
    dateInput.min = localDate;
  }

  const notice = document.getElementById("notice");
  const form = document.getElementById("appointmentForm");
  const askButton = document.getElementById("askButton");
  const question = document.getElementById("question");
  const answer = document.getElementById("answer");

  askButton.addEventListener("click", async () => {
    const q = question.value.trim();
    if (!q) { answer.textContent = "Please enter a question."; question.focus(); return; }
    askButton.disabled = true; askButton.textContent = "Thinking…"; answer.textContent = "Waiting for a response…";
    try {
      const res = await fetch("/api/faq", {method:"POST", headers:{"Content-Type":"application/json"}, body:JSON.stringify({question:q})});
      const data = await res.json();
      if (!res.ok) throw new Error(data.message || "Request failed");
      answer.textContent = data.answer || "No answer available.";
    } catch (e) { answer.textContent = "The assistant is temporarily unavailable. Please try again."; }
    finally { askButton.disabled = false; askButton.textContent = "Ask"; }
  });
  question.addEventListener("keydown", e => { if (e.key === "Enter") askButton.click(); });

  form.addEventListener("submit", async e => {
    e.preventDefault();
    const payload = Object.fromEntries(new FormData(form).entries());
    try {
      const res = await fetch("/appointments", {method:"POST", headers:{"Content-Type":"application/json"}, body:JSON.stringify(payload)});
      if (!res.ok) {
        const err = await res.json().catch(() => ({}));
        throw new Error(err.message || "Please check your details and try again.");
      }
      notice.className = "notice success";
      notice.textContent = "Appointment request saved. This is a request, not a confirmed booking.";
      form.reset();
      dateInput.min = new Date(Date.now() - new Date().getTimezoneOffset() * 60000).toISOString().slice(0,10);
    } catch (err) { notice.className = "notice error"; notice.textContent = err.message; }
  });
});
