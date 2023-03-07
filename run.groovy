
def notify = new com.YcNotify()
def u = new com.YcDsl()

u.apps "cover", "safeguard"
// u.apps
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

def yc_params = []
u.apps.each {
  yc_params.add(
    "${it.toLowerCase().capitalize()}"
  )
}
println yc_params

println u.apps?.find { true }

// notify.started()

// println u.actions
