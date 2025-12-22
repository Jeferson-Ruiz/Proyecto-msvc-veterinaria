// Funciones para editar empleados
async function openEditModal(employeeCode) {
  try {
    // 1. Mostrar código
    document.getElementById('employeeCode').textContent = employeeCode;
    
    // 2. Obtener datos actuales
    const response = await fetch(`http://localhost:8082/employee/code/${employeeCode}`);
    const employee = await response.json();
    
    // 3. Llenar formulario con datos REALES
    document.getElementById('editEmail').value = employee.email || '';
    document.getElementById('editPhone').value = employee.phoneNumber || '';
    document.getElementById('editArea').value = employee.workArea || 'SALUD';
    document.getElementById('editContract').value = employee.contractType || 'TEMPORAL';
    document.getElementById('editStatus').value = employee.status || 'ACTIVE';
    
  } catch (error) {
    console.error('Error cargando datos:', error);
    alert('Error al cargar datos del empleado');
  }
  
  // 4. Mostrar modal
  const modal = new bootstrap.Modal(document.getElementById('editModal'));
  modal.show();
}

async function saveEmployeeChanges() {
  const code = document.getElementById('employeeCode').textContent;
  const email = document.getElementById('editEmail').value;
  const phone = document.getElementById('editPhone').value;
  const area = document.getElementById('editArea').value;
  const contract = document.getElementById('editContract').value;
  const status = document.getElementById('editStatus').value;
  
  try {
    // Actualizar email
    await fetch(`http://localhost:8082/employee/update-email/${code}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email: email })
    });
    
    // Actualizar teléfono
    await fetch(`http://localhost:8082/employee/update-phone/${code}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ phoneNumber: phone })
    });
    
    // Actualizar área (si tienes endpoint)
    // await fetch(`http://localhost:8082/employee/update-area/${code}`, {...});
    
    // Actualizar contrato
    await fetch(`http://localhost:8082/employee/update-contract/${code}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(contract)
    });
    
    // Actualizar estado
    await fetch(`http://localhost:8082/employee/update-status/${code}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(status)
    });
    
    alert('✅ Empleado actualizado');
    location.reload(); // Recargar página
    
  } catch (error) {
    console.error('Error actualizando:', error);
    alert('❌ Error al actualizar');
  }
  
  // Cerrar modal
  const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
  if (modal) modal.hide();
}