function imperfection = getImperfection(vehID)
%getImperfection Returns driver's imperfection.
%   imperfection = getImperfection(VEHID) Returns the driver's imperfection
%   (dawdling) [0,1]

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getImperfection.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
imperfection = traci.vehicle.getUniversal(constants.VAR_IMPERFECTION, vehID);