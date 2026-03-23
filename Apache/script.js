const API_BASE_URL = "https://localhost:5000";

const registerForm = document.getElementById("register-form");
const loginForm = document.getElementById("login-form");
const apiResponse = document.getElementById("api-response");

function showResponse(title, status, payload) {
	apiResponse.textContent = `${title}\nstatus: ${status}\n\n${JSON.stringify(payload, null, 2)}`;
}

async function requestJson(url, options) {
	const response = await fetch(url, {
		headers: {
			"Content-Type": "application/json"
		},
		...options
	});

	let data;
	try {
		data = await response.json();
	} catch (_) {
		data = { mensaje: "La API no devolvio JSON." };
	}

	return { status: response.status, data };
}

registerForm.addEventListener("submit", async (event) => {
	event.preventDefault();

	const email = document.getElementById("correo-registro").value.trim();
	const password = document.getElementById("password-registro").value;

	try {
		const { status, data } = await requestJson(`${API_BASE_URL}/users`, {
			method: "POST",
			body: JSON.stringify({ email, password })
		});
		showResponse("REGISTER", status, data);
	} catch (error) {
		showResponse("REGISTER", "ERROR", { mensaje: error.message });
	}
});

loginForm.addEventListener("submit", async (event) => {
	event.preventDefault();

	const email = document.getElementById("correo-ingreso").value.trim();
	const password = document.getElementById("password-ingreso").value;

	try {
		const { status, data } = await requestJson(`${API_BASE_URL}/users/login`, {
			method: "POST",
			body: JSON.stringify({ email, password })
		});
		showResponse("LOGIN", status, data);
	} catch (error) {
		showResponse("LOGIN", "ERROR", { mensaje: error.message });
	}
});
