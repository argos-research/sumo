function typeID = getTypeID(vehID)
%getTypeID Returns the id of the type of the named vehicle.
%   typeID = getTypeID(VEHID) Returns the id of the type of the named 
%   vehicle.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getTypeID.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
typeID = traci.vehicle.getUniversal(constants.VAR_TYPE, vehID);