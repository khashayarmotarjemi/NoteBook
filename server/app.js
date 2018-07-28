var express = require('express');
var app = express();

// body parser
var bodyParser = require('body-parser')
app.use(bodyParser.json());       // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
    extended: true
}));

// mongo
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/test');

var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function () {
    console.log("connected to databse")
});

var NoteBook = require('./models/notebook').notebook
var Note = require('./models/notebook').note

// creates new note c
app.post('/note/create/:notebook_id', function (req, res) {
    console.log(req.params.notebook_id)

    NoteBook.findById(
        req.params.notebook_id,
        function (err, notebook) {
            if (err) return console.log(err)

            var note = new Note({
                text: req.body.text,
                title: req.body.title
            })

            notebook.notes.push(note)
            notebook.save(function (err2, notebok) {
                if (err2) return console.log(err2)
                res.send("new note added")
            })
        }
    )
})

// create notebook c
app.post('/notebook/create', function (req, res) {
    var notebook = new NoteBook({
        name: req.body.name,
        color: req.body.color
    })

    notebook.save(function (err) {
        if (err) return console.log(err)
        res.send('notebook created')
    })


})

// get all notebooks r
app.get('/notebook', function (req, res) {
    NoteBook.find(function (err, notebooks) {
        res.send(notebooks)
    })
})

// update note text and/or title u
app.post('/note/update/:notebook_id/:note_id', function (req, res) {
    var noteId = req.params.note_id
    var notebookId = req.params.notebook_id
    console.log(notebookId)
    console.log(noteId)

    if (req.body.text) {
        NoteBook.update({
            _id: notebookId,
            'notes._id': noteId
        }, {$set: {'notes.$.text': req.body.text}}, function (err) {
            if (err) console.log(err)
        });
    }

    if (req.body.title) {
        NoteBook.update({
            _id: notebookId,
            'notes._id': noteId
        }, {$set: {'notes.$.title': req.body.title}}, function (err) {
            if (err) console.log(err)
        });
    }

    res.send("finished");
});

// update notebook u
app.post('/notebook/update/:notebook_id', function (req, res) {
    var notebookId = req.params.notebook_id
    var newName = req.body.name
    var newColor = req.body.color

    NoteBook.findById(notebookId, function (err, notebook) {
        if (err) console.log(err)

        if (newName) {
            notebook.name = newName
        }
        if (newColor) {
            notebook.color = newColor
        }

        notebook.save(function (err) {
            if (err) console.log(err)
        })

        res.send("updated")
    })
})

// remove note d
app.get('/note/remove/:notebook_id/:note_id', function (req, res) {
    var noteId = req.params.note_id
    var notebookId = req.params.notebook_id
    console.log(notebookId)
    console.log(noteId)

    NoteBook.findById(notebookId, function (err, notebook) {
        notebook.notes.id(noteId).remove()
        notebook.save(function (err) {
            if (err) console.log(err)
        })
        res.send("deleted")
    })
})

// remove notebook d
app.get('/notebook/remove/:notebook_id', function (req, res) {
    var notebookId = req.params.notebook_id

    NoteBook.remove({"_id": notebookId}, function (err) {
        if (err) console.log(err)
    })

    res.send("notebook removed")

})


app.listen(3000)

