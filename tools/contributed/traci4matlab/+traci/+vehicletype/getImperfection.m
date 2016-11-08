function imperfection = getImperfection(typeID)
%getImperfection Returns the driver's imperfection of vehicles of this type.
%   imperfection = getImperfection(TYPEID) Returns the driver's imperfection
%   (dawdling) [0,1] of vehicles of this type.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getImperfection.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
imperfection = traci.vehicletype.getUniversal(constants.VAR_IMPERFECTION, typeID);