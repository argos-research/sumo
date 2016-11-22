function routeID = getRouteID(vehID)
%getRouteID Returns the id of the route of the named vehicle.
%   routeID = getRouteID(VEHID) Returns the id of the route of the named 
%   vehicle.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getRouteID.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
routeID = traci.vehicle.getUniversal(constants.VAR_ROUTE_ID, vehID);