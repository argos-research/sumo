function setRouteID(vehID, routeID)
%setRouteID Sets the id of the route for the named vehicle.
%   setRouteID(VEHID,ROUTEID) Sets the id of the route for the named 
%   vehicle.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setRouteID.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
traci.sendStringCmd(constants.CMD_SET_VEHICLE_VARIABLE, constants.VAR_ROUTE_ID, vehID, routeID);