
def u = new com.YcDsl()

u.apps "cover", "safeguard"
u.agent "my-agent"
u.daysToKeeplogs 30
u.upstreams "up"
u.downstreams "down", "test"

// u.actions {
//   name "testin"
// }

println u.apps
println u.agent
println u.daysToKeeplogs
println u.upstreams
println u.downstreams

// println u.actions
