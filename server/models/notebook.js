var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var NoteSchema = new Schema({
    title: 'String',
    date: 'Date',
    text: 'String',
    color: 'String'
})

var NoteBookSchema = new Schema({
    name: 'String',
    date: 'Date',
    color: 'String',
    notes: [NoteSchema]
})

module.exports = {
    notebook: mongoose.model('NoteBook', NoteBookSchema),
    note: mongoose.model('Note', NoteSchema)
}


