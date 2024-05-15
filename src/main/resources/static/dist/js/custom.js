$(document).ready(function() {
    $('#userTable').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": "/api/users",
        "columns": [
            // { "data": "id" },
            { "data": "firstName" },
            { "data": "lastName" },
            { "data": "email" },
            { "data": "phoneNumber" },
            {
                "data": null,
                "render": function(data, type, row) {
                    return '<a class="btn btn-primary" href="/users/edit/'+row.id+'"><i class="fas fa-edit"></i></a>\
                    <a class="btn btn-secondary" href="/users/'+row.id+'"><i class="fas fa-eye"></i></a>\
                    <a onclick="return confirm(`Are you sure you want to delete this record?`)" class="btn btn-danger" href="/users/delete/'+row.id+'"><i class="fas fa-trash"></i></a>';
                }
            }
        ]
    });
});