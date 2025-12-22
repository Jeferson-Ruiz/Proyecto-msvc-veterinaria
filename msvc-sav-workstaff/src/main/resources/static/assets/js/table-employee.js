// table-employee.js
fetch('http://localhost:8082/employee')
  .then(response => response.json())
  .then(data => {
    console.log('Datos:', data);
    
    const tbody = document.querySelector('#employeeTable tbody');
    tbody.innerHTML = '';
    
    data.forEach(emp => {
      // ESCAPE las comillas simples en los nombres
      const safeName = emp.fullName.replace(/'/g, "\\'");
      
      tbody.innerHTML += `
        <tr>
          <td>
            <a href="users-profile.html?codigo=${emp.employeeCode}" class="text-primary">
              ${emp.employeeCode}
            </a>
          </td>
          <td>${emp.fullName}</td>
          <td>${emp.documentNumber}</td>
          <td>${emp.email}</td>
          <td>${emp.phoneNumber}</td>
          <td>${emp.workArea}</td>
          <td><span class="badge bg-success">${emp.status}</span></td>
          <td>
            <button class="btn btn-sm btn-warning" onclick="openEditModal('${emp.employeeCode}')">
              <i class="bi bi-pencil"></i>
            </button>
            <button class="btn btn-sm btn-danger" onclick="openDeleteModal('${emp.employeeCode}', '${safeName}')">
              <i class="bi bi-trash"></i>
            </button>
          </td>
        </tr>
      `;
    });
  })
  .catch(error => {
    console.error('Error:', error);
  });