const index = {
  init : function () {
    const _this = this;
    $('#btn-save').on('click', function () {
      _this.save();
    });
    $('#btn-update').on('click', function () {
      _this.update();
    });
    $('#btn-delete').on('click', function () {
      _this.delete();
    });
  },
  save : function () {
    const data = {
      title: $('#title').val(),
      author: $('#author').val(),
      content: $('#content').val()
    };

    $.ajax({
      type: 'POST',
      url: '/api/posts',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function () {
      alert('Post is saved.');
      window.location.href = '/';
    }).fail(function (error) {
      alert(JSON.stringify(error));
    });
  },
  update: function() {
    const data = {
      title: $('#title').val(),
      content: $('#content').val()
    };

    const id = $('#id').val();

    $.ajax({
      type: 'PUT',
      url: '/api/posts/' + id,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function() {
      alert('Post is modified.');
      window.location.href = '/';
    }).fail(function(error) {
      alert(JSON.stringify(error));
    });
  },
  delete: function() {
    const id = $('#id').val();

    $.ajax({
      type: 'DELETE',
      url: '/api/posts/' + id,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8'
    }).done(function() {
      alert('Post is removed.');
      window.location.href = '/';
    }).fail(function(error) {
      alert(JSON.stringify(error));
    });
  }
};

index.init();