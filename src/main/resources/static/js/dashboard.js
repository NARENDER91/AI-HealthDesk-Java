document.addEventListener("DOMContentLoaded", async () => {
  const loading = document.getElementById("loading"), table = document.getElementById("appointmentsTable"), rows = document.getElementById("rows"), empty = document.getElementById("empty"), notice = document.getElementById("notice");
  const escapeHtml = value => String(value ?? "").replace(/[&<>"']/g, c => ({"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#39;"}[c]));
  async function load() {
    try {
      const res = await fetch("/api/appointments");
      if (!res.ok) throw new Error("Could not load appointments. Check database settings.");
      const data = await res.json();
      loading.hidden = true;
      document.getElementById("total").textContent = data.length;
      document.getElementById("pending").textContent = data.filter(x => x.status === "Requested").length;
      document.getElementById("completed").textContent = data.filter(x => x.status === "Completed").length;
      if (!data.length) { empty.hidden = false; table.hidden = true; return; }
      table.hidden = false; empty.hidden = true;
      rows.innerHTML = data.map(a => `<tr>
        <td><strong>${escapeHtml(a.patientName)}</strong><br><small>${escapeHtml(a.contactEmail)}</small></td>
        <td>${escapeHtml(a.preferredDate)}</td><td>${escapeHtml(a.requestType)}</td>
        <td class="status-cell">${escapeHtml(a.status)}</td>
        <td><select class="status-select" id="status-${a.id}" aria-label="Status for request ${a.id}"><option ${a.status==="Requested"?"selected":""}>Requested</option><option ${a.status==="Contacted"?"selected":""}>Contacted</option><option ${a.status==="Completed"?"selected":""}>Completed</option><option ${a.status==="Cancelled"?"selected":""}>Cancelled</option></select><br><button class="button primary update-button" data-id="${a.id}">Save status</button></td>
      </tr>`).join("");
      rows.querySelectorAll("button[data-id]").forEach(btn => btn.addEventListener("click", async () => {
        const id = btn.dataset.id, status = document.getElementById(`status-${id}`).value;
        try {
          const r = await fetch(`/api/appointments/${id}/status`, {method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({status})});
          if (!r.ok) throw new Error("Could not update status.");
          notice.className = "notice success"; notice.textContent = "Status updated."; await load();
        } catch(e) { notice.className = "notice error"; notice.textContent = e.message; }
      }));
    } catch(e) { loading.textContent = e.message; }
  }
  await load();
});
