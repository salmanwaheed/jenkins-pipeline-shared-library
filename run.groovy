
def x = [start: 'one', "name": "hello"]
x.get("name", "test")
println x

// def args = [:]
def args = [commands: [echo: "hello, world"]]
args.get("commands", [])
if ( !args.commands ) return;
println args
